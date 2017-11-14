var express = require('express');
var router = express.Router();

const User = require('../models/User')
/* GET users listing. */
router.get('/', async(req, res, next) => {
  const users = await User.find()
  res.send(users);
})

router.get('/me', async(req, res, next) => {
  try {

    res.send(req.user);
    
  } catch (err) {
    next(err)
  }
})

router.put('/', async(req, res, next) => {
  try {
    var updatedUser = await User.findByIdAndUpdate(req.user._id,  {$set : req.body }, { new: true, runValidators: true })
    updatedUser = await updatedUser.save()

    // var updatedUser = await User.findById(req.user._id)

    // updatedUser = Object.assign(updatedUser, req.body)

    // updatedUser = await User(req.body).save()
    
    res.send(updatedUser);

  } catch (err) {
    // console.log(err)
    next(err)
  }
})


module.exports = router;
