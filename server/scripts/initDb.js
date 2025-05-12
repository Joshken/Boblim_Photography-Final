const mongoose = require('mongoose');
const Photo = require('../models/Photo');
require('dotenv').config();

const samplePhotos = [
    {
        title: 'Wedding Ceremony',
        description: 'Beautiful wedding ceremony shot',
        imageUrl: 'https://example.com/wedding1.jpg',
        category: 'Wedding'
    },
    {
        title: 'Portrait Session',
        description: 'Professional portrait photography',
        imageUrl: 'https://example.com/portrait1.jpg',
        category: 'Portrait'
    }
];

const initDb = async () => {
    try {
        await mongoose.connect(process.env.MONGODB_URI || 'mongodb+srv://baguiojosh:htk1BuE5YYDEXzEE@blpapplication.fs2ws3z.mongodb.net/boblim_photography');
        console.log('Connected to MongoDB Atlas');

        // Clear existing data
        await Photo.deleteMany({});
        console.log('Cleared existing photos');

        // Insert sample data
        await Photo.insertMany(samplePhotos);
        console.log('Added sample photos');

        console.log('Database initialized successfully');
        process.exit(0);
    } catch (error) {
        console.error('Error initializing database:', error);
        process.exit(1);
    }
};

initDb(); 