ALTER TABLE encouragement
  MODIFY encouraging_text TEXT;

TRUNCATE encouragement;
INSERT INTO encouragement (number_of_images, encouraging_text, encouraging_image_path, logout_button)
VALUES (5,
        '<h1>Well done! You are halfway through</h1> <h2>The temporal lobe:</h2>Great work! You just unlocked the temporal lobe. The temporal is involved in perceiving and remembering sounds. It also works in conjunction with the hippocampus to form conscious memories. <h3>The case of Henry Molaison 1926-2008: </h3> From an early age, Henry suffered from severe epilepsy. In an effort to control his seizures, he had an operation that removed a large part of his temporal lobe and hippocampus on both sides. Whilst the surgery cured him of his epilepsy, tragically he was left unable to form new memories.   ',
        '/images/encouragement/1.png', FALSE),
  (7,
   '<h1>Finished your first 50!</h1><h2>The parietal lobe:</h2>Well done – you just unlocked the parietal lobe!  The parietal lobe contains an area of gray matter called the sensory strip. This processes sensory information such as pain, touch and temperature. <h3>The parietal lobe:</h3>The sensory strip contains a map of our body. This is called the somatosensory homunculus and is shown below. When you touch something soft with your hand, this information will travel up to the sensory strip to where the hand is mapped on the homunculus.',
   '/images/encouragement/2.png', TRUE),
  (10, '<h1>Well done!</h1>', '/images/encouragement/2.png', TRUE);