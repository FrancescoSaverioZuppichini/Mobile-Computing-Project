module.exports = function(server) {
    console.log("Starting ws server...")
    var io = require('socket.io')(server);
    io.on('connection', function(socket){ 
        console.log("WEE")

        socket.on("test", (msg)=>{
            console.log(msg)
        })
        socket.on("foo", (msg)=>{
            console.log("**********************FOOO**************")
        })

        socket.on('disconnect', function(){
            console.log('user disconnected');
          });
    });
}
