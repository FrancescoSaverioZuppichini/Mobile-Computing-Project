var mongoose = require('mongoose')
var Schema = mongoose.Schema

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
});

module.exports = mongoose.model('User', userSchema);
