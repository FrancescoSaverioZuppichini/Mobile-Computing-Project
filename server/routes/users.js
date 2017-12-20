var express = require('express');
var router = express.Router();
// var ObjectId = require('mongodb').ObjectId; 
var ObjectId = require('mongoose').mongo.ObjectId
var jwt = require('jsonwebtoken-refresh')

const User = require('../models/User')
/* GET users listing. */
router.get('/', async(req, res, next) => {
  const users = await User.find()
  res.send(users);
})

router.get('/me', async(req, res, next) => {
  try {
    res.json(req.user);

  } catch (err) {
    next(err)
  }
})

router.put('/refresh/me', async(req, res, next) => {

  const refleshed = jwt.refresh(req.decoded, '12h', process.env.TOKEN_SECRET)

  res.json({
    token: refleshed
  })
})

router.get('/nearby', async(req, res, next) => {
  const coordinates = req.user.location.coordinates
  try {
    const user = await User.findById(req.user._id)
    const users = await user.getNeighbors()
    res.json(users)
  } catch (err) {
    next(err)
  }
})

router.put('/', async(req, res, next) => {  
  try {
    var updatedUser = await User.findByIdAndUpdate(req.user._id, {
      $set: req.body
    }, {
      new: true,
      runValidators: true
    })

    updatedUser = await updatedUser.save()
    updatedUser = updatedUser.toObject()
    delete updatedUser.password
    // console.log(updatedUser)
    res.send(updatedUser);

  } catch (err) {
    next(err)
  }
})


module.exports = router;
