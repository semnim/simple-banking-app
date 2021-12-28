# simple-(terminal-)banking-app

Simple banking app simulation via terminal. Practise project to learn SQL, improve code clarity & exception handling, and learn working with external libraries / building tools.

## Decription
Creates an SQLite database in the project folder to track changes, created "credit-cards", "pin-numbers", etc.

Essentially an implementation of the "Luhn algorithm" for credit-card number creation and verification of validity via checksum digit as used in industry.

### Functionality

Takes command line configuration "-fileName exampleDbName.s3db" to read specific database, or resorts to default database name "card.s3db". If it already exists, card.s3db will be read from, otherwise it will be created.

Consists of main-menu loop and log-in-menu loop.

*Main loop:* 
  
  - Create new account
  - log in
  - quit

*Log in loop after successful authentification:*

  - See balance
  - Add deposit
  - Transfer to other account in database
  - close account, i.e. delete from database
  - log out
  - exit

### Showcase

<img width="570" alt="login" src="https://user-images.githubusercontent.com/82184629/147613558-01e10e5e-3349-4235-af7e-af557b9e7a34.png">

<img width="570" alt="transfer" src="https://user-images.githubusercontent.com/82184629/147613578-d9e3da41-c6fe-452d-81b9-40a7feac0c4c.png">

<img width="570" alt="logout" src="https://user-images.githubusercontent.com/82184629/147613588-9fc6a7cf-0b51-489f-9bff-bb52a2bc10a1.png">
