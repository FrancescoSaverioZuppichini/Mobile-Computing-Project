var user = { email: 'test@com', password: '123' }

var fieldsToUpdate = [ { email: 'test2@com', },  {password: '1234'}, { role: 'VOLUNTEER'}, { medicInfo : {blood : 'A+'} } , { position: { lat: 40.0, long: 30.0 } } ]

var newUser = { email: 'test3@com', role: 'USER', medicInfo: {blood : 'A-'}, position: { lat: 40.0, long: 30.0 } }

module.exports = function (chai, server, should) {
  it('it should return a token if the credential are correct', (done) => {
    chai.request(server)
      .put('/auth/')
      .send(user)
      .end(function (err, res) {
        res.should.have.status(200)
        res.should.have.be.a('object')
        res.body.should.have.property('token')
        // store it
        user.token = res.body.token
        done()
      })
  })
  it('it should return myself', (done) => {
    chai.request(server)
      .get('/api/users/me/')
      .set('Authorization', 'Bearer ' + user.token)
      .end(function (err, res) {
        res.should.have.status(200);
        res.body.should.have.be.a('object')
        res.body.should.have.have.property('email').eql(user.email)
        user = Object.assign(user, res.body)
        // TODO no password should be sent back
        done();
      });
  });
  it('it should update email field correctly', (done) => {
    chai.request(server)
    .put('/api/users/')
    .set('Authorization', 'Bearer ' + user.token)
    .send(fieldsToUpdate[0])    
    .end(function (err, res) {
      res.should.have.status(200);
      res.body.should.have.be.a('object')
      res.body.should.have.have.property('email').eql(fieldsToUpdate[0].email)
      // TODO no password should be sent back
      done();
    })
  })
  it('it should update password field correctly', (done) => {
    chai.request(server)
    .put('/api/users/')
    .set('Authorization', 'Bearer ' + user.token)
    .send(fieldsToUpdate[1])    
    .end(function (err, res) {

      res.should.have.status(200);
      res.body.should.have.be.a('object')
      res.body.should.have.property('password').not.eql(user.password)
      // TODO no password should be sent back
      done();
    })
  })
  it('it should update role field correctly', (done) => {
    chai.request(server)
    .put('/api/users/')
    .set('Authorization', 'Bearer ' + user.token)
    .send(fieldsToUpdate[2])    
    .end(function (err, res) {

      res.should.have.status(200);
      res.body.should.have.be.a('object')
      res.body.should.have.property('role').eql(fieldsToUpdate[2].role)
      done();
    })
  })
  it('it should not update role field if it is not correct', (done) => {
    chai.request(server)
    .put('/api/users/')
    .set('Authorization', 'Bearer ' + user.token)
    .send({ role: 'WIZARD' })    
    .end(function (err, res) {
      // TODO should be 400
      res.should.have.status(500);
      done();
    })
  })
  it('it should update medicInfo field correctly', (done) => {
    chai.request(server)
    .put('/api/users/')
    .set('Authorization', 'Bearer ' + user.token)
    .send(fieldsToUpdate[3])    
    .end(function (err, res) {
      res.should.have.status(200);
      res.body.should.be.a('object')
      res.body.should.have.property('medicInfo')
      res.body.medicInfo.should.have.property('blood').eql(fieldsToUpdate[3].medicInfo.blood)
      done();
    })
  })
  it('it should update position field correctly', (done) => {
    chai.request(server)
    .put('/api/users/')
    .set('Authorization', 'Bearer ' + user.token)
    .send(fieldsToUpdate[4])    
    .end(function (err, res) {
      res.should.have.status(200);
      res.body.should.be.a('object')
      res.body.should.have.property('position')
      res.body.position.should.have.property('lat').eql(fieldsToUpdate[4].position.lat)
      res.body.position.should.have.property('long').eql(fieldsToUpdate[4].position.long)
      
      done();
    })
  })
  it('it should update each field', (done) => {
    chai.request(server)
    .put('/api/users/')
    .set('Authorization', 'Bearer ' + user.token)
    .send(newUser)    
    .end(function (err, res) {
      res.should.have.status(200);
      res.body.should.be.a('object')
      for(let key in newUser){
        res.body.should.have.property(key)
        res.body[key].should.be.eql(newUser[key])
      }
      done();
    })
  })
}
