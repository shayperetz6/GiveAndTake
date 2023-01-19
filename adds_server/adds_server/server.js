const express = require("express");
const bodyParser = require('body-parser');
const {getDatabase, ref, set} = require('firebase/database');
const {initializeApp} = require('firebase/app');
// how to install nodejs in windows :
// https://stackoverflow.com/a/27344046
//Create instance of express server
const app = express();
//declare port
const port = 3000;

//firebase config
const firebaseConfig = {
    apiKey: "AIzaSyCJxRZZ1JADKa70Kc7kh_CdeF5ibTQ-jvk",
    authDomain: "give-and-take-c5a5f.firebaseapp.com",
    databaseURL: "https://give-and-take-c5a5f-default-rtdb.firebaseio.com",
    projectId: "give-and-take-c5a5f",
    storageBucket: "give-and-take-c5a5f.appspot.com",
    messagingSenderId: "603061167992",
    appId: "1:603061167992:web:6b510bf3aa51199a2bca8d",
    measurementId: "G-KVK3RE5159"
};
const appFirebase = initializeApp(firebaseConfig);
//init realtime db
const db = getDatabase(appFirebase);

app.use(express.json());
app.use(bodyParser.urlencoded({extended: false}));

/**
 * Get
 */
//Sample hello world
app.get('/api', (req, res) => {
    res.send('Hello World!')
});

/**
 * Post
 */
//Post - method receive json do stuff...
//for our application we receive json(body) as post(add) and send it to firebase realtime
//Create new routes
app.post('/api/post', async (req, res) => {
    if (!req.body) res.status(500).send('routes not sent correctly"');
    const postId = req.body.postid;
    if (!postId) res.status(400).send('must send post id');
    try {
        await set(ref(db, `Posts/${postId}`), req.body);
        res.status(200).json({"response" : "success"})
    } catch (e) {
        res.status(400).send('failed save routes')
    }
});

//Start server
//for start server in terminal do(same path of project) -> node server.js
app.listen(port, () => {
    console.log(`AddServer app listening on port ${port}`)
});

