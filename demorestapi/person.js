const express = require('express'); //Import Express
const Joi = require('joi'); //Import Joi
const app = express(); // Create Express Application on the app variable
app.use(express.json()); //used the json file

//Give data to the server
const persons = [
    {name: 'George', id:1},
    {name: 'Josh', id:2},
    {name: 'Tyler', id:3},
    {name: 'Alice', id:4},
    {name: 'Candice', id:5}
]

//Read Request Handlers
//Display the message when the URL consist of '/'
app.get('/', (req, res) => {
    res.send('Welcome to Person API!');
});

//Display the List of persons when URL consists of api persons
app.get('/api/persons', (req, res)=> {
    res.send(persons);
});

//Display the Information of Specific person when tou metion the id
app.get('/api/persons/:id', (req, res) => {
    const person = persons.find(c => c.id === parseInt(req.params.id));
    //If there is no valid person Id, then display an error with the following message
    if(!person) res.status(404).send('<h2 style="font-family:Malgun Gothic; color: darkred;">Ooops... Cant find what you are looking for!</h2>');
    res.send(person);
})

//CREATE Request Handler
//CREATE New person Information
app.post('/api/persons', (req, res) => {
    
    const { error } = validateperson(req.body);
    if(error) {
        res.status(400).send(error.details[0].message)
        return;
    }

    //Increment the person id
    const person = {
        id: persons.length + 1,
        name: req.body.name
    };
    persons.push(person);
    res.send(person);
});

//Update Request Handler
//Update Existing person Information
app.put('/api/persons/:id', (req, res) => {
    const person = persons.find(c=> c.id === parseInt(req.params.id));
    if(!person) res.status(404).send('<h2 style="font-family: Malgun Gothic; color: darkred;">Ooops... Cant find what you are looking for!</h2>');

    const { error } = validateperson(req.body);
    if(error) {
        res.status(400).send(error.details[0].message);
        return;
    }

    person.name = req.body.name;
    res.send(person);
})

//Delete Request Handler
//Delete person Details
app.delete('/api/persons/:id', (req, res) => {
    const person = persons.find(c=> c.id === parseInt(req.params.id));
    if(!person) res.status(404).send('<h2 style= "font-family: Malgun Gothic; color: darkred;">Ooops... Cant find what you are looking for!</h2>');
    
    const index = persons.indexOf(person);
    persons.splice(index, 1);

    res.send(person);
})

//Validate Information
function validateperson(person) {
    const schema = Joi.object({
        name: Joi.string().min(3).required()
    });

    return schema.validate(person);
}

//PORT ENVIRONMENT VARIABLE
const port = process.env.PORT || 8180;
app.listen(port, () => console.log(`Listening on port ${port}..`));