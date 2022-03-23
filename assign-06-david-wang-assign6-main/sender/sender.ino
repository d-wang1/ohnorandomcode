/* 
 *  
 *  CSE 132 - Assignment 6
 *  
 *  Fill this out so we know whose assignment this is.
 *  
 *  Name: David Wang
 *  WUSTL Key: 498153
 *  
 *  and if two are partnered together
 *  
 *  Name:
 *  WUSTL Key:
 */


unsigned long lastTime;
int deltaTime = 1000;
char c[] = "Message";
const int potenPin = A0;
const int echoPin = 12;
const int trigPin = 13;
const int criticalPotenReading = 800;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT); 
  pinMode(echoPin, INPUT);
  sendInfo(c);
}

void loop() {
  // put your main code here, to run repeatedly:
  unsigned long mil = millis();
  
  if(mil > lastTime + deltaTime) {
    lastTime = mil;
    Serial.write(0x21);
    Serial.write(0x32);
    for(int i=31;i>=0;i--) {
      Serial.write((mil>>(i*4))%16);
    }

    Serial.write(0x21);
    Serial.write(0x33);
    unsigned int poten = analogRead(potenPin);
    for(int i=15;i>=0;i--) {
      Serial.write((poten>>(i*4))%16);
    }

    Serial.write(0x21);
    Serial.write(0x34);
    unsigned long rawTime = getEchoTime();
    for(int i=31;i>=0;i--) {
      Serial.write((rawTime>>(i*4))%16);
    }
    
    if(poten > criticalPotenReading) {
      Serial.write(0x21);
      Serial.write(0x31);
      Serial.write("High alarm");
      Serial.write(0x00);
    }
  }

}

void sendInfo(char* c) {
  Serial.write(0x21);
  Serial.write(0x30);
  Serial.write(c);
  Serial.write(0x00);
}


float getEchoTime()
{
  unsigned long echoTime;           //variable to store the time it takes for a ping to bounce off an object
  float calculatedDistance;         //variable to store the distance calculated from the echo time

  //send out an ultrasonic pulse that's 10ms long
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  echoTime = pulseIn(echoPin, HIGH);      //use the pulsein command to see how long it takes for the
                                          //pulse to bounce back to the sensor

  return echoTime;              //send back the distance that was calculated
}
