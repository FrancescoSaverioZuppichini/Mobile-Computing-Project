//
//  BloodController.swift
//  serverPackageDescription
//
//  Created by Francesco Zuppichini on 27.10.17.
//

import Vapor
import HTTP

final class BloodController {
    func index(_ req: Request) throws -> ResponseRepresentable {
        return try Blood.all().makeJSON()
    }
    
    func store(_ req: Request) throws -> ResponseRepresentable {
        let newBlood = try req.blood()
        try newBlood.save()
        
        return newBlood
    }
    
}

extension BloodController: ResourceRepresentable{
    func makeResource() -> Resource<Blood> {
        return Resource(
            index: index,
            store: store
        )
    }
}

extension Request {
    func blood() throws -> Blood {
        guard let json = json else { throw Abort.badRequest }
        return try Blood(json: json)
    }
}

extension BloodController: EmptyInitializable { }
