module.exports = function(server) {
    // TODO use redis!!
    var users = {}

    console.log("Starting ws server...")
    var io = require('socket.io')(server);
    io.on('connection', function(socket){ 
        console.log("WEE")
        
        socket.on("identify_me", (id) => {
            users[id] = socket
            socket.user_id = id
        })

        socket.on("test", (msg)=>{
            console.log(msg)
        })

        socket.on("help", (data) =>{
            console.log(socket.user_id)
            console.log(data)
            // TODO fetch the closest user
        })

        socket.on('disconnect', function(){
            console.log('user disconnected');
          });
    });
}
