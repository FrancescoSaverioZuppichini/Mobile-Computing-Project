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
            var user = await User.findById(socket.user_id)
            var neighbors = await user.getNeighbors()
            // CHECK: should I remove myself?
            neighbors.shift()
            // console.log(neighbors);
// 
            neighbors.forEach( neighbor => {
                const socket = users[neighbor._id]
                const isOnline = socket != null
                if(isOnline){
                    // CHECK: we should create a new obj
                    user.dist = neighbor.dist
                    console.log(neighbor.email)
                    
                    socket.emit("help_request", { from: user } )
                }   else{
                    // CHECK put a notification?
                }
            })
            // store neighbors in the socket CHECK: is it dangerous?
            socket.neighbors = neighbors
        })

        socket.on("help_end", async (from) => {
            var user = await User.findById(socket.user_id)
            console.log(`user ${user.email} does not required helpm anymore!`)

            socket.neighbors.forEach( neighbor => {
                const socket = users[neighbor._id]
                const isOnline = socket != null
                if(isOnline){
                    // CHECK: we should create a new obj
                    user.dist = neighbor.dist
                    console.log(`Broadcast to ${neighbor.email}`)
                    
                    socket.emit("help_end_success", { from: user } )
                }   else{
                    // CHECK put a notification?
                }
            })
        })

        socket.on("help_accepted", async (to) => {
            to = JSON.parse(to)
          
            const toSocket = users[to._id]
            const isOnline = toSocket != null

            var user = await User.findById(socket.user_id)
            
            var toUser = await User.findById(to._id)

            if(isOnline){
                console.log(user, "ACCEPTED")
                toSocket.emit("help_accepted_success", { from: user })
            }

            socket.neighbors = [toUser]
        })

        socket.on("update", async(user) => {
            var user = await User.findById(socket.user_id)
            console.log(`${user.email} just udpate something!`)

            socket.neighbors.forEach( neighbor => {
                const socket = users[neighbor._id]
                const isOnline = socket != null
                if(isOnline){
                    // CHECK: we should create a new obj
                    user.dist = neighbor.dist
                    // console.log(neighbor.email)
                    
                    socket.emit("update_user", { from: user } )
                }   else{
                    // CHECK put a notification?
                }
            })
            
        })

        socket.on("help_stop", async(data) => {
            var user = await User.findById(socket.user_id)

            socket.neighbors.forEach( neighbor => {
                const socket = users[neighbor._id]
                const isOnline = socket != null
                if(isOnline){
                    socket.emit("help_stop", { from: user } )
                } 
            })

            socket.neighbors = []
        } )

        socket.on("help_stop_user", async (who) => {
            who = JSON.parse(who)
            console.log(who.email)
            const toSocket = users[who._id]
            const isOnline = toSocket != null

            var user = await User.findById(socket.user_id)

            toSocket.neighbors = toSocket.neighbors.filter(neighbor => neighbor.email != user.email)

            if(isOnline){
                toSocket.emit("help_stop_user_success", { from: user })
            }
        })

        socket.on('disconnect', function(){
            console.log('user disconnected');

            delete users[socket.user_id]
          });
    });
}
