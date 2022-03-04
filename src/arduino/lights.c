#include <Adafruit_NeoPixel.h>

#define N_LEDS 60
#define PIN     6

Adafruit_NeoPixel strip = Adafruit_NeoPixel(N_LEDS, PIN, NEO_GRB + NEO_KHZ800);

void setup() {
  strip.begin();
  Serial.begin(9600);
}

int pos = 0, dir = 1;

void loop() {
  long color = 0; //Set 0 if off, 1 if color, 2 if blue
  // red = 0xff0000;
  // blue = 0x0000ff;
  // orange = ff3300
  if(Serial.available()){
      color = Serial.parseInt();
  }

  strip.setPixelColor(pos - 2, color);
  strip.setPixelColor(pos - 1, color);
  strip.setPixelColor(pos    , color);
  strip.setPixelColor(pos + 1, 0);
  strip.setPixelColor(pos + 2, 0);
  strip.setPixelColor(pos + 3, 0);
  strip.setPixelColor(pos + 4, 0);
  strip.setPixelColor(pos + 5, color);
  strip.setPixelColor(pos + 6, color);
  strip.setPixelColor(pos + 7, color);
  strip.setPixelColor(pos + 8, 0);
  strip.setPixelColor(pos + 9, 0);
  strip.setPixelColor(pos + 10, 0);
  strip.setPixelColor(pos + 11, 0);
  strip.setPixelColor(pos + 12, color);
  strip.setPixelColor(pos + 13, color);
  strip.setPixelColor(pos + 14, color);
  strip.setPixelColor(pos + 15, 0);
  strip.setPixelColor(pos + 16, 0);
  strip.setPixelColor(pos + 17, 0);
  strip.setPixelColor(pos + 18, 0);
  strip.setPixelColor(pos + 19, color);
  strip.setPixelColor(pos + 20, color);
  strip.setPixelColor(pos + 21, color);
  strip.setPixelColor(pos + 22, 0);
  strip.setPixelColor(pos + 23, 0);
  strip.setPixelColor(pos + 24, 0);
  strip.setPixelColor(pos + 25, 0);
  strip.setPixelColor(pos + 26, color);
  strip.setPixelColor(pos + 27, color);
  strip.setPixelColor(pos + 28, color);
  strip.setPixelColor(pos + 29, 0);
  strip.setPixelColor(pos + 30, 0);
  strip.setPixelColor(pos + 31, 0);
  strip.setPixelColor(pos + 32, 0);
  strip.setPixelColor(pos + 33, color);
  strip.setPixelColor(pos + 34, color);
  strip.setPixelColor(pos + 35, color);
  strip.setPixelColor(pos + 36, 0);
  strip.setPixelColor(pos + 37, 0);
  strip.setPixelColor(pos + 38, 0);
  strip.setPixelColor(pos + 39, 0);
  strip.setPixelColor(pos + 40, color);
  strip.setPixelColor(pos + 41, color);
  strip.setPixelColor(pos + 42, color);
  strip.setPixelColor(pos + 43, 0);
  strip.setPixelColor(pos + 44, 0);
  strip.setPixelColor(pos + 45, 0);
  strip.setPixelColor(pos + 46, 0);
  strip.setPixelColor(pos + 47, color);
  strip.setPixelColor(pos + 48, color);
  strip.setPixelColor(pos + 49, color);
  strip.setPixelColor(pos + 50, 0);
  strip.setPixelColor(pos + 51, 0);
  strip.setPixelColor(pos + 52, 0);
  strip.setPixelColor(pos + 53, 0);
  strip.setPixelColor(pos + 54, color);
  strip.setPixelColor(pos + 55, color);
  strip.setPixelColor(pos + 56, color);

  strip.show();
  delay(25); // How fast the lights blink

  int j;
  // Causes an illusion that lights are travelling infinitely by resetting the location of the colors after a short time
  if(dir > 0){
    for(j=-2; j<= 2; j++) strip.setPixelColor(pos+j, 0);
  }

  pos += dir;
  if(pos > 5) {
      for(j=0; j<= strip.numPixels(); j++) strip.setPixelColor(j, 0);
    pos = 1;
  }
}