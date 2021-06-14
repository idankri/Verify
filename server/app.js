'use strict'

// imports
const express = require('express');
const session = require('express-session');
const bodyParser = require('body-parser');
const Airtable = require('airtable');
const { Console } = require('console');

// Airtable authentication
Airtable.configure({
    endpointUrl: 'https://api.airtable.com',
    apiKey: 'keyeSqlNWGOAWrdCw'
});

// 'Verify' base import
const base = Airtable.base('app2dxKmALjcdGNJN');

// init app
let app = express();
app.use(bodyParser.json());
app.use(session({
    secret: "shhhh",
    resave: false,
    saveUninitialized: true
}));

/**
 * Express route for retrieving a 'Review'
 * Request parameters:
 * None
 * Body parameters: 
 * city (string)
 * street (string)
 * number (integer)
 * floor (integer)
 * apartment (integer)
 */
 app.get('/review', (req, res) => {

    let city = req.body.city;
    let street = req.body.street;
    let number = req.body.number;
    let floor = req.body.floor;
    let apartment = req.body.apartment;

    let success = false;

    let filterRecordsFormula = `And({City} = '${city}', {Address and number} = '${street} ${number}', {Floor} = '${floor}', {Apartment number} = '${apartment}')`;
    
    if(city == undefined || street == undefined || number == undefined || floor == undefined || apartment == undefined){
        res.status(400).json({status: "Fail", message: "Missing or wrong body parameters"});
    }else{
        base('Reviews').select({
            // Selecting the review with the required body params

            filterByFormula: filterRecordsFormula,
            view: "Data"
        }).eachPage(function page(records, fetchNextPage) {
            // This function (`page`) will get called for each page of records.
            
            if(records == null || records == undefined || records.length == 0){
                res.status(401).json({status: "Fail", message: "Record does not exist"});
            }

            records.forEach(function(record) {

                if(record == null || record == undefined){
                    res.status(401).json({status: "Fail", message: "Record does not exist"});
                }

                console.log('Retrieved Review:', record.get('Id'));

                let aroundTags = {};
                let maintenanceTags = {};

                // Retrieve Around tags record
                base('Around tags').find(record.get("Around tags")[0], function(err, record1) {
                    if (err) { 
                        console.error(err); 
                        res.status(500).json({status: "Fail", message: "Server error occured"});
                        return; 
                    }

                    console.log('Retrieved Around tag:', record1.id);

                    aroundTags["Id"] = record1.get('Id');
                    aroundTags["Neighbors"] = record1.get('Neighbors');
                    aroundTags["Accessibility"] = record1.get('Accessibility');
                    aroundTags["Construction near by"] = record1.get('Construction near by');
                    aroundTags["Parking"] = record1.get('Parking');
                    aroundTags["Supermarkets"] = record1.get('Supermarkets');
                    aroundTags["Public transport"] = record1.get('Public transport');
                    aroundTags["Bars and restaurants"] = record1.get('Bars and restaurants');
                    aroundTags["Dog gardens"] = record1.get('Dog gardens');
                    aroundTags["Public parks"] = record1.get('Public parks');

                    // Retrieve Maintenance tags record
                    base('Maintenance tags').find(record.get("Maintenance tags")[0], function(err, record2) {
                        if (err) { 
                            console.error(err); 
                            res.status(500).json({status: "Fail", message: "Server error occured"});
                            return; 
                        }

                        console.log('Retrieved Maintenance tag:', record2.id);

                        maintenanceTags["Id"] = record2.get('Id');
                        maintenanceTags["Cellular reception"] = record2.get('Cellular reception');
                        maintenanceTags["Winter temp"] = record2.get('Winter temp');
                        maintenanceTags["Acoustic insulation"] = record2.get('Acoustic insulation');
                        maintenanceTags["Moisture and mold problems"] = record2.get('Moisture and mold problems');
                        maintenanceTags["Kitchen plumbing problems"] = record2.get('Kitchen plumbing problems');
                        maintenanceTags["Summer temp"] = record2.get('Summer temp');
                        maintenanceTags["Air conditioning"] = record2.get('Air conditioning');
                        maintenanceTags["Moisture and mold area"] = record2.get('Moisture and mold area');
                        maintenanceTags["Kitchen storage"] = record2.get('Kitchen storage');
                        maintenanceTags["Kitchen convenience"] = record2.get('Kitchen convenience');
                        maintenanceTags["Shower stream"] = record2.get('Shower stream');
                        maintenanceTags["Shower plumbing problems"] = record2.get('Shower plumbing problems');
                        maintenanceTags["Water heater"] = record2.get('Water heater');

                        res.json({
                            "Id": record.get('Id'), 
                            "Staying period": record.get("Staying period"),
                            "Enter date": record.get("Enter date"),
                            "Still living in the apartment": record.get("Still living in the apartment"),
                            "Exit date": record.get("Exit date"),
                            "City": record.get("City"),
                            "Address and number": record.get("Address and number"),
                            "Floor": record.get("Floor"),
                            "Apartment number": record.get("Apartment number"),
                            "Tenants composition": record.get("Tenants composition"),
                            "Landlord rating": record.get("Landlord rating"),
                            "Landlord tags": record.get("Landlord tags"),
                            "Landlord free text": record.get("Landlord free text"),
                            "Maintenance rating": record.get("Maintenance rating"),
                            "Maintenance free text": record.get("Maintenance free text"),
                            "Around rating": record.get("Around rating"),
                            "Around tags": aroundTags,
                            "Maintenance tags": maintenanceTags,
                            "Around free text": record.get("Around free text"),
                            "Pictures": record.get("Pictures")
                        });
                    });
                });
            });
        
            // To fetch the next page of records, call `fetchNextPage`.
            // If there are more records, `page` will get called again.
            // If there are no more records, `done` will get called.
            fetchNextPage();
        
        }, function done(err) {
            if (err) { 
                console.error(err); 
                res.status(500).json({status: "Fail", message: "Server error occured"});
                return; 
            }
        });
    }
});

app.listen(3000, () => {
    console.log("Listening on port 3000");
});