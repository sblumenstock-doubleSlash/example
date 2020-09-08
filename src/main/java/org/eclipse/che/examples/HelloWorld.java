/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Red Hat, Inc. All rights reserved.
 *  Licensed under the MIT License. See LICENSE in the project root for license information.
 *--------------------------------------------------------------------------------------------*/
package org.eclipse.che.examples;

import java.util.Arrays;
import java.util.Scanner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class HelloWorld {
    public static void main(final String... argvs) {

        final String user = "user";
        final String database = "ideas";
        final String password = "password";
        final MongoCredential credentials = MongoCredential.createCredential(user, database, password.toCharArray());

        final MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017),
                Arrays.asList(credentials));
        final DB db = mongoClient.getDB(database);
        final DBCollection collection = db.getCollection("ideas");
        System.out.println("Connected to database!");

        boolean stop = false;
        final Scanner scanner = new Scanner(System.in);

        while (stop != true) {
            System.out.println("Please insert your idea: ");
            final String name = scanner.nextLine();
            if (name.equals("end")) {
                stop = true;
                scanner.close();
                mongoClient.close();
            } else if (name.equals("show")) {
                DBCursor query = collection.find();
                while(query.hasNext()) {
                    DBObject dbo = query.next();
                    System.out.println(dbo.toString());
                }
                if(query !=null)
                {
                    query.close();
                }   

            } else {
                final BasicDBObject idea = new BasicDBObject();
                idea.put("id", name);
                idea.put("idea", name);
                collection.insert(idea);
                System.out.println("Amount of ideas: " + collection.count());
            }
        }
    }
}
