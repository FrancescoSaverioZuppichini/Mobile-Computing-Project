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

final class Blood: Model {
    let storage = Storage()
    
    var type: String
    
    init(row: Row) throws {
        type = try row.get("type")
    }
    
    init(type: String) {
        self.type = type
        
    }
    
    func makeRow() throws -> Row {
        var row = Row()
        try row.set("type", type)
        return row
    }
}

extension Blood: Preparation {
    static func prepare(_ database: Database) throws {
        try database.create(self) { builder in
            builder.id()
            builder.string("type")
        }
    }
    
    static func revert(_ database: Database) throws {
        try database.delete(self)
    }
}

extension Blood {
    var users: Children<Blood, User>{
        return children()
    }
}

extension Blood: ResponseRepresentable {}


extension Blood: JSONRepresentable {
    convenience init(json: JSON) throws {
        try self.init(
            type: json.get("type")
        )
    }
    
    func makeJSON() throws -> JSON {
        var json = JSON()
        try json.set("type", type)
      
        return json
    }
}
