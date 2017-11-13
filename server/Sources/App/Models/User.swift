//
//  Blood.swift
//  serverPackageDescription
//
//  Created by Francesco Zuppichini on 27.10.17.
//

import Vapor
import FluentProvider
import HTTP
import JSON

final class User: Model {
    let storage = Storage()
    let bloodId: Identifier?

    var name: String
    
    init(row: Row) throws {
        name = try row.get("name")
        bloodId = try row.get("blood_id")
    }
    
    init(name: String, bloodId: Identifier?) {
        self.name = name
        self.bloodId = bloodId
    }
    
    func makeRow() throws -> Row {
        var row = Row()
        try row.set("name", name)
        return row
    }
}

extension User: Preparation {
    static func prepare(_ database: Database) throws {
        try database.create(self) { builder in
            builder.id()
            builder.string("name")
            builder.parent(Blood.self)
        }
    }
    
    static func revert(_ database: Database) throws {
        try database.delete(self)
    }
}

extension User {
    var blood: Parent<User, Blood>{
        return parent(id: bloodId)
    }
}

extension User: ResponseRepresentable {}


extension User: JSONRepresentable {
    convenience init(json: JSON) throws {
        try self.init(
            name: json.get("name")
        )
    }
    
    func makeJSON() throws -> JSON {
        var json = JSON()
        try json.set("name", name)
        
        return json
    }
}

