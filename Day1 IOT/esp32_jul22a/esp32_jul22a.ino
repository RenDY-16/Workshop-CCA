#define BLYNK_TEMPLATE_ID "TMPL60ed4jpz0"
#define BLYNK_TEMPLATE_NAME "ESP32"
#define BLYNK_AUTH_TOKEN "W142F4d4ZTSqBIlLNC8viKx-sIrayTqs"

#include <WiFi.h>
#include <BlynkSimpleEsp32.h>

char ssid[] = "UMI Connect 2.4G";
char pass[] = "umiukhuwah";

#define LED_PIN 5

BLYNK_WRITE(V0) {
  int pinValue = param.asInt();
  digitalWrite(LED_PIN, pinValue);
  Serial.println(pinValue == 1 ? "LED ON" : "LED OFF");
}

void setup() {
  Serial.begin(115200);
  pinMode(LED_PIN, OUTPUT);
  
  WiFi.begin(ssid, pass);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nWiFi Connected!");
  
  Blynk.config(BLYNK_AUTH_TOKEN, "sgp1.blynk.cloud", 80);
  Blynk.connect();
}

void loop() {
  Blynk.run();
}