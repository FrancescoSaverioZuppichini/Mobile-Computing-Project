var express = require('express');
var router = express.Router();
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
   
    res.json({ token: refleshed })
})

router.put('/', async(req, res, next) => {
  console.log(req.body)

  try {
    var updatedUser = await User.findByIdAndUpdate(req.user._id, {
      $set: req.body
    }, {
      new: true,
      runValidators: true
    })

    updatedUser = await updatedUser.save()

    res.send(updatedUser);

  } catch (err) {
    next(err)
  }
})


module.exports = router;
