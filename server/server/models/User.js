var mongoose = require('mongoose')
var Schema = mongoose.Schema

var bcrypt = require('bcrypt')

var userSchema = new Schema({
  name: {
    required: true,
    type: String
  },
  role: {
    required: true,
    type: String,    
    enum: ['USER', 'VOLUNTEER']
  }
})
// hash password before store it 
UserSchema.pre("save", async function (next) {
  
     if (!this.isModified("password")) {
         return next();
     }
     try {
         const hash = await bcrypt.hashAsync(this.password, 16.5)
  
         this.password = hash
  
         next()
  
     } catch (err) {
         next(err)
     }
  
  });

UserSchema.methods.passwordIsValid = bcrypt.compareAsync(password, this.password)

module.exports = mongoose.model('User', userSchema);
