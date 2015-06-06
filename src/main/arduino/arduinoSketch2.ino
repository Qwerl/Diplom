#define LED_1       8
#define LED_2       9
#define LED_3       10
#define LED_4       11
#define LED_5       12
#define LED_6       13

#define LEDS_ARRAY_SIZE 6

char inputs   [20];
char oldInputs[20];
int leds[LEDS_ARRAY_SIZE] = {LED_1, LED_2, LED_3, LED_4, LED_5, LED_6};
int workingLed = LED_6;

void setup() {
    Serial.begin(9600);

    pinMode(LED_1, OUTPUT);
    pinMode(LED_2, OUTPUT);
    pinMode(LED_3, OUTPUT);
    pinMode(LED_4, OUTPUT);
    pinMode(LED_5, OUTPUT);
    pinMode(LED_6, OUTPUT);
}

void loop() {
   
    getInputs();
    strcpy(oldInputs, inputs);
    Serial.println(inputs);

    if(Serial.available()){
        int ind=0;
        char buff[5];
        while(Serial.available()){
            unsigned char c = Serial.read();
            buff[ind] = c;
            if(ind++ > 6) break;
        }
        buff[2]=0;
        if(strcmp(buff, "L1")==0){
            ledWrite(LED_1);
        }
        if(strcmp(buff, "L2")==0){
            ledWrite(LED_2);
        }
        if(strcmp(buff, "L3")==0){
            ledWrite(LED_3);
        }
        if(strcmp(buff, "L4")==0){
            ledWrite(LED_4);
        }
        if(strcmp(buff, "L5")==0){
            ledWrite(LED_5);
        }
        if(strcmp(buff, "L6")==0){
            ledWrite(LED_6);
        }
    }
    
    delay(1000);
}

void getInputs(){
    sprintf(inputs, "hello -%i- ", workingLed);
}

void ledWrite(int led) {
    for(int i = 0; i < LEDS_ARRAY_SIZE; i++) {
        if(leds[i] == led) {
            digitalWrite(leds[i], HIGH);
            workingLed = leds[i];
        } else {
            digitalWrite(leds[i], LOW);
        }
    }
}
