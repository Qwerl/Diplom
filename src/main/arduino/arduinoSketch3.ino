#include <LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

char inputs   [20];
char oldInputs[20];

char welcomeMessage [2][17] = {
  {"  Hello  world  "},
  {"   azazazaaza   "}
};
char endingMessage [2][17] = {
  {"  ending  work  "},
  {"    goodbye!    "}
};
char freeMessage [2][17] = {
  {"    Resource    "},
  {"    is free.    "}
};

int BUFFER_SIZE = 8;

void setup() {
  Serial.begin(9600);
  lcd.begin(16, 2);
}

void loop() {
  strcpy(oldInputs, inputs);
  if (Serial.available()) {
    lcd.setCursor(0, 0);
    char buff[BUFFER_SIZE];
    clearBuffer(buff);
    readInputs(buff);

    buff[BUFFER_SIZE] = 0;
    delay(1000);
    if (strstr(buff, "work")) {
      work(buff);
    } else if (strstr(buff, "free")) {
      dispose();
    } else if (strstr(buff, "stop")) {
      stopW();
    } else if (strstr(buff, "start")) {
      start();
    } else {
      defaultOperation();
    }
  }
  delay(1000);
}

void readInputs(char buff[]) {
  int index = 0;
  while (Serial.available()) {
    if (index < BUFFER_SIZE) {
      unsigned char c = Serial.read();
      buff[index] = c;
      index++;
      buff[index] = '\0';
    } else {
      break;
    }
  }
}

void clearBuffer(char buff[]) {
  for (int i = 0; i < BUFFER_SIZE; i++) buff[i] = 0;
}

void start() {
  lcd.setCursor(0, 0);
  lcd.print(welcomeMessage[0]);
  lcd.setCursor(0, 1);
  lcd.print(welcomeMessage[1]);
}

void stopW() {
  lcd.setCursor(0, 0);
  lcd.print(endingMessage[0]);
  lcd.setCursor(0, 1);
  lcd.print(endingMessage[1]);
}

void work(char buff[]) {
  lcd.setCursor(0, 0);
  lcd.print("thread #");
  lcd.setCursor(8, 0);
  int i;
  for (i = 4; i < BUFFER_SIZE; i++) {
    if (isdigit(buff[i])) {
      lcd.print(buff[i]);
    } else {
      break;
    }
  }
  for (; i < 17; i++) {
    lcd.print(" ");
  }

  lcd.setCursor(0, 1);
  for (i = 0; i <= 10; i++) {
    lcd.setCursor(0, 1);
    for (int j = 0; j < i; j++) {
      lcd.write((uint8_t)0xFF);
    }
    lcd.setCursor(10, 1);
    lcd.print(i*10);
    lcd.print("%");
    delay(1000);
  }
  lcd.setCursor(0, 1);
  lcd.print("   Work done.   ");
  delay(2000);
  Serial.print("workDone");
}

void dispose() {
  lcd.setCursor(0, 0);
  lcd.print(freeMessage[0]);
  lcd.setCursor(0, 1);
  lcd.print(freeMessage[1]);
}

void defaultOperation() {
  //todo
}