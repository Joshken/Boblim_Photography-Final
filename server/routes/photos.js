const express = require('express');
const router = express.Router();
const Photo = require('../models/Photo');

// Get all photos
router.get('/', async (req, res) => {
  try {
    const photos = await Photo.find();
    res.json(photos);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Add a new photo
router.post('/', async (req, res) => {
  const photo = new Photo({
    title: req.body.title,
    description: req.body.description,
    imageUrl: req.body.imageUrl,
    category: req.body.category
  });

  try {
    const newPhoto = await photo.save();
    res.status(201).json(newPhoto);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Get a specific photo
router.get('/:id', async (req, res) => {
  try {
    const photo = await Photo.findById(req.params.id);
    if (photo) {
      res.json(photo);
    } else {
      res.status(404).json({ message: 'Photo not found' });
    }
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

module.exports = router; 