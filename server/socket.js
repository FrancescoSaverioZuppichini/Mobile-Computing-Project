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
            const user = await User.findById(socket.user_id)
            const neighbors = await user.getNeighbors()
            // TODO remove it added only for debugging
            neighbors.forEach( neighbor => {
                console.log(neighbor.email)
                if(users[neighbor._id]){
                    users[neighbor._id].emit("help_request", { from: user } )
                }
            } )
            // TODO fetch the closest user
        })

        socket.on('disconnect', function(){
            console.log('user disconnected');
          });
    });
}
