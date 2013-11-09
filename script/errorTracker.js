(function(){
    var LOG_AMMOUNT = 25;
    var mode = {INFO:0, ERROR:1};

    function LogData(err, msg, type){
        if(err){
            this.error = err;
        }
        this.msg = msg || "";
        this.type = (type === mode.INFO) ? "INFO" : "ERROR";
    };


    function jsLogger(){

        this.data = [];
        this.os = navigator.platform;
        this.browser = navigator.appVersion;

    };

    jsLogger.init = function(url){

        var logger = new jsLogger();
        logger.setUrl(url);
        logger.backupPreviousErrorHandler(window.onerror);
        window.onerror = function(msg, url, line){ logger.defaultErrorHandler(msg, url, line); };
        window.onbeforeunload = function(){
           logger.flushIfNeeded(true);
        };
        return logger;
    }

    jsLogger.prototype = {
        setUrl: function(url){
            this.serverPath = url;
        },

        logInfo: function(msg){
            this._log(null, msg, mode.INFO);
        },

        logError: function(err, msg){
            this._log(err, msg, mode.ERROR);
        },

        _log: function(err, msg, mode){
            var data = new LogData(err, msg, mode);
            this.data.push(data);
            this.flushIfNeeded();
        },

        flushIfNeeded: function(forced){
            if((forced || this.data.length > LOG_AMMOUNT) && this.data.length > 0){
                this._send();
                this.data = [];
            }
        },

        defaultErrorHandler: function myErrorHandler(errorMsg, url, lineNumber) {
            if (this.previousErrorHandler) {
                return this.previousErrorHandler(errorMsg, url, lineNumber);
            }
            var msg =  "Error occurred in page: " + url +
                "\nat line number:" + lineNumber +
                "\nMessage:" + errorMsg;
            this.logError(null, msg);
            return false;
        },

        backupPreviousErrorHandler: function(handler){
            this.previousErrorHandler = handler;
        },

        _send: function(){
            $.ajax({
                type: 'POST',
                url: this.serverPath,
                crossDomain: true,
                data: { os:this.os, browser:this.browser, data: this.data },
                dataType: 'json',
                error: function (responseData, textStatus, errorThrown) {
                    if(console){
                        console.log(textStatus);
                        console.log(errorThrown);
                    }
                }
            });
        }
    };

    window.errorLogger = jsLogger;

})();


