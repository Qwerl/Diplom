#define LED_WORK        13
#define LED_NOT_WORK    12

char inputs   [20];
char oldInputs[20];

bool started = false;

void setup() {
    Serial.begin(9600);

    pinMode(LED_WORK , OUTPUT);
    pinMode(LED_NOT_WORK, OUTPUT);
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
        if(strcmp(buff, "hi")==0){
            started = true;
        }
        if(strcmp(buff, "bye")==0){
            started = false;
        }
    }

    if(started){
        work();
    } else {
        notWork();
    }
    
    delay(1000);
}

void getInputs(){
    sprintf(inputs, "hello %s", started? "true" : "false");
}

void work() {
    digitalWrite(LED_WORK, HIGH);
    digitalWrite(LED_NOT_WORK, LOW);
}

void notWork() {
    digitalWrite(LED_WORK, LOW);
    digitalWrite(LED_NOT_WORK, HIGH);
}