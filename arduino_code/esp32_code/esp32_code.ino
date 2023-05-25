//Librerias
    #include "DHTesp.h"
    #include <Keypad.h>
    #include <WiFi.h>
    #include <MySQL_Connection.h>
    #include <MySQL_Cursor.h>
    #include <TimeLib.h>

//frecuencia medida sensores 
    unsigned long previousTime = 40000;  // Variable para almacenar el tiempo anterior
    const unsigned long interval = 40000;  // Intervalo de tiempo en milisegundos
      
//Variable para almacenar valor leído del sensor
    DHTesp dht;
    int SENSOR; 
    String encadenado;
    int id_nevera=8;
    char fechaHora[20];
       
//Definición de la distribucion de teclas teclado matricial 4x4
    const uint8_t ROWS = 4; //numero de filas
    const uint8_t COLS = 4;//numero de columnas
    char keys[ROWS][COLS] = {
      { '1', '2', '3', 'A' },
      { '4', '5', '6', 'B' },
      { '7', '8', '9', 'C' },
      { '*', '0', '#', 'D' }
    };
    
//pines de conexión
    int pinDHT = 23;
    const int HALL_PIN = 21; 
    const int pinLED = 12;
    byte rowPins[ROWS] = {18, 5, 14, 13}; // Pines de las filas del teclado
    byte colPins[COLS] = {4, 0, 2, 15}; // Pines de las columnas del teclado
    //Variable teclado
      Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, ROWS, COLS);

//Datos de conexión a la bd remota
    const char* ssid = "iPhone de Dani"; // Ssid wifi
    const char* password = "gutiguti";   // Contraseña WiFi
    IPAddress server(195,235,211,197);   // Dirección IP del servidor MariaDB
    char user[] = "pri_fridgedeck";      // Usuario de la base de datos
    char passwordDB[] = "fridgedeck1";     // Contraseña del usuario
    char database[] = "`prifridgedeck`";   // Nombre de la base de datos
    int port = 3306;                     // Puerto de conexión
    //Variable para la conexión a la base de datos
      WiFiClient client;
      MySQL_Connection conn((Client *)&client);

void setup() {
  
  Serial.begin(9600); // Inicializa comunicación serie a 9600 bps
  
  //Configurar la sincronización de tiempo mediante NTP
      configTime(0, 0, "pool.ntp.org"); 
  //Conexión WiFi
      WiFi.begin(ssid, password);
      while (WiFi.status() != WL_CONNECTED) {
        delay(1000);
        Serial.println("Conectando a WiFi...");
      }

  //Establecer la conexión a la base de datos
      if (conn.connect(server, port, user, passwordDB)) {
        Serial.println("Conexión exitosa a la base de datos");
       // Seleccionar la base de datos
    char query[100];
    sprintf(query, "USE %s", database);
    MySQL_Cursor *cursor = new MySQL_Cursor(&conn);
    cursor->execute(query);
    delete cursor;

    Serial.println("Base de datos seleccionada: " + String(database));
    } else {
        Serial.println("Error al conectar a la base de datos");
      }

  //Inicializamos el dht
     dht.setup(pinDHT, DHTesp::DHT11);
  
  //Establece el pin del sensor Hall como entrada
     pinMode(HALL_PIN, INPUT);
     
  //Establece el pin del led como salida
     pinMode(pinLED, OUTPUT);
}
String obtenerFechaHoraActual() {
  time_t tiempoActual = time(nullptr);
  struct tm *tiempoLocal = gmtime(&tiempoActual);
  int anio = tiempoLocal->tm_year + 1900;
  int mes = tiempoLocal->tm_mon + 1;
  int dia = tiempoLocal->tm_mday;
  int hora = tiempoLocal->tm_hour + 2;
  int minuto = tiempoLocal->tm_min;
  int segundo = tiempoLocal->tm_sec;
  
  char fechaHora[20];
  sprintf(fechaHora, "%04d-%02d-%02d %02d:%02d:%02d", anio, mes, dia, hora, minuto, segundo);
  
  return String(fechaHora);
}

void loop() {
  
  // Obtener el tiempo actual
  unsigned long currentTime = millis();
  
  // Verificar si ha transcurrido el intervalo de tiempo
  if (currentTime - previousTime >= interval) {
  
      //Lectura de datos
          //Obtenemos el arreglo de datos de humedad y temperatura
             TempAndHumidity data = dht.getTempAndHumidity();
             float temperature = dht.getTemperature();
             float humidity = dht.getHumidity();
             
         //Lee el valor del sensor Hall
             SENSOR = digitalRead(HALL_PIN);
    
         //Obtener los componentes de la fecha
              time_t tiempoActual = time(nullptr);
              struct tm *tiempoLocal = gmtime(&tiempoActual);
              int anio = tiempoLocal->tm_year + 1900;
              int mes = tiempoLocal->tm_mon + 1;
              int dia = tiempoLocal->tm_mday;
              int hora = tiempoLocal->tm_hour+2;
              int minuto = tiempoLocal->tm_min;
              int segundo = tiempoLocal->tm_sec;
              sprintf(fechaHora, "%04d-%02d-%02d %02d:%02d:%02d", anio, mes, dia, hora, minuto,segundo); 
    
     
     //Gestion datos sensor Hall
        if (SENSOR == HIGH) { // Si se detecta un campo magnético
          Serial.println("---");
          Serial.println("Campo detectado");
          boolean estado = false;
            //Consulta SQL INSERT estado puerta
            char query[200];
            sprintf(query, "INSERT INTO Puerta (valor, fecha, id_nevera) VALUES (%d, '%s', %d)", estado, fechaHora,id_nevera);
    
            //Crear un objeto MySQL_Cursor para ejecutar la consulta
                MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
            //Ejecutar la consulta
                cur_mem->execute(query);
            //Liberar memoria
                delete cur_mem;
                delay(150);
                
        }else{
          Serial.println("---");
          Serial.println("Campo NO detectado");
          boolean estado = true;
          
            //Consulta SQL INSERT estado puerta
            char query[200];
            sprintf(query, "INSERT INTO Puerta (valor, fecha, id_nevera) VALUES (%d, '%s', %d)", estado, fechaHora,id_nevera);
    
            //Crear un objeto MySQL_Cursor para ejecutar la consulta
                MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
            //Ejecutar la consulta
                cur_mem->execute(query);
            //Liberar memoria
                delete cur_mem;
                delay(150);
        }
  
    //Gestion datos Sensor humedad y temperatura
        Serial.println("Temperatura: " + String(data.temperature, 2) + "°C");
        Serial.println("Humedad: " + String(data.humidity, 1) + "%");
        Serial.println("---");
         
          //Consulta SQL INSERT temperatura
            char query[200];
            sprintf(query, "INSERT INTO Temperatura (valor, fecha, id_nevera) VALUES (%f, '%s', %d)", temperature, fechaHora, id_nevera);
    
            //Crear un objeto MySQL_Cursor para ejecutar la consulta
                MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
            //Ejecutar la consulta
                cur_mem->execute(query);
            //Liberar memoria
                delete cur_mem;
                delay(150);
    
          //Consulta SQL INSERT humedad
            char query2[200];
            sprintf(query2, "INSERT INTO Humedad (valor, fecha, id_nevera) VALUES (%f, '%s', %d)", humidity, fechaHora, id_nevera);
            
            //Crear un objeto MySQL_Cursor para ejecutar la consulta
                MySQL_Cursor *cur_mem2 = new MySQL_Cursor(&conn);
            //Ejecutar la consulta
                cur_mem2->execute(query2);
            //Liberar memoria
              delete cur_mem;
              delay(150);

   //Actualizar el tiempo anterior al actual
     previousTime = currentTime;
    }
    //Lectura de datos
      //Obtenemos caracter introducido por teclado
        char key = keypad.getKey();
    //Gestion datos teclado matricial 4x4
      if (key) {
         if (key =='*') {
            //Enciende el led
              digitalWrite(pinLED, HIGH);
              
            //Envía a monitor serial la tecla presionada
              Serial.println("Añadir:" + encadenado);
            
            //Consulta SQL UPDATE
              char query[200];
              snprintf(query, sizeof(query), "UPDATE Productos SET fecha = CURRENT_TIMESTAMP, stock = stock + 1 WHERE code = '%s'", encadenado.c_str());
            
            //Crear un objeto MySQL_Cursor para ejecutar la consulta
              MySQL_Cursor *cur_mem = new MySQL_Cursor(&conn);
               
            //Ejecutar la consulta
              cur_mem->execute(query);

            //Ejecutar la consulta
              int result = cur_mem->execute(query);
            
            //Verificar si la actualización se realizó exitosamente
              if (result == 1) {
                  // La actualización fue exitosa
                  Serial.println("La actualización se realizó correctamente.");
              } else {
                  // No se pudo realizar la actualización o hubo un error
                  Serial.println("No se pudo realizar la actualización.");
              }


            //Liberar memoria
              delete cur_mem;
              delay(400);
               
            //Eliminamos los caracteres ya leidos
              encadenado="";
              
            //Apagar el LED
              digitalWrite(pinLED, LOW);
        }else{
          encadenado += key; // Concatena la tecla presionada a la cadena
          Serial.println("Code: " + encadenado);
          }
        }
    }
