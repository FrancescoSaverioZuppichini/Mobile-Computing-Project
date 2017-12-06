const User = require('./models/User')

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

        socket.on("help", async (data) =>{
            console.log(socket.user_id)
            console.log(data)
            var user = await User.findById(socket.user_id)
            var neighbors = await user.getNeighbors()
            // CHECK: should I remove myself?
            // neighbors.shift()
            
            neighbors.forEach( neighbor => {
                const socket = users[neighbor._id]
                const isOnline = socket != null

                if(isOnline){
                    socket.emit("help_request", { from: user } )
                }
            } )
        })

        socket.on('disconnect', function(){
            console.log('user disconnected');
          });
    });
}
