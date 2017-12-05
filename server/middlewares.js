var jwt = require('jsonwebtoken')
const User = require('./models/User')

const errors = { TOKEN_NOT_PROVIDED: { message: "Token not provided" }}

function JWTProtected(req, res, next) {
  try {
    if (!req.token) throw errors.TOKEN_NOT_PROVIDED

    jwt.verify(req.token, process.env.TOKEN_SECRET);

    var decoded = jwt.decode(req.token, { complete: true })
    req.decoded = decoded
    req.user = decoded.payload.data
    // password MUST not be showed to the client even if its hashed
    delete req.user.password
    
    next()
  } catch (err) {
    err.status = 401
    next(err)
  }
}

module.exports = {
  JWTProtected
}
