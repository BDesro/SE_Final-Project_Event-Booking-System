Run this in terminal after downloading the jar to open program: java --module-path "C:\Program Files\Java\javafx-sdk-24\lib" --add-modules javafx.controls,javafx.fxml -jar OmniVent.jar

# SE_Final-Project_Event-Booking-System

## Product Backlog Sprint 2

<ins>**Event page**</ins>  
- [x] Seating - All the stuff that goes with this  
- [x] Take me to checkout button  
- [x] GIPHY API 
- [X] User Profile button  
      
<ins>**Checkout Screen**</ins>  
- [x] List of seats in cart  
- [x] Confirmation screen (simple text box saying it was purchased)  
      
<ins>**User Profile**</ins>  
- [x] Add Name    
- [x] User Profile Pic  
- [x] CC Info  
- [x] My Events
- [x] Clean Up Functionality (clear text boxes, etc.)
      
<ins>**Admin Screen**</ins>  
- [x] Venue Manager  
- [x] Venue Class  
      
<ins>**Login Screen**</ins>  
- [x] Password Requirements (1 Cap, 1 lower, 1 num, 1 special)  
      
<ins>**Database**</ins>  
- [x] Add Venue  
- [x] Add Join Venue-Event (Just a venue FK in Event_List)  
- [x] Update Bookings   
- [x] Add Table for Images  

<ins>**Other**</ins>  
- [X] HELP BUTTON FOR EVERY SCREEN (video later)  
- [x] Green-Black Colors
- [x] Left Buttons Right Everything Else
- [x] Main Screen (Company Logo, Buttons to login, guest login, etc.)  

TYPE THIS INTO NOTEPAD AND INSERT CHANGE TO LOCATION FOR YOUR COMPUTER, ONCE DONE SAVE IT AS A '.bat' FILE AND OPEN THAT FILE TO RUN THIS
@echo off
java --module-path "C:\Program Files\Java\javafx-sdk-24\lib" --add-modules javafx.controls,javafx.fxml -jar [LOCATION OF THE .jar FILE]
pause
