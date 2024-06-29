# Proyecto: Consumo de API de Fotos de Mars Rover
## Descripción
Este proyecto es una aplicación Java que consume la API de Mars Rover Photos de la NASA. La aplicación le permite seleccionar diferentes rovers, cámaras y "sol" (días marcianos) para ver las fotos tomadas por los rovers en Marte. La interfaz gráfica está construida usando Java Swing.
# Manual del Usuario
## Vista Principal
Al iniciar la aplicación, se presenta una ventana principal con los siguientes componentes:

### Selección de Sol y Cámara: 
Dos desplegables le permiten seleccionar un día marciano (sol) y una cámara específica del rover.
### Área de Información: 
Un área de texto donde se muestra información sobre la búsqueda y los resultados.
### Panel de Miniaturas: 
Un panel donde se muestran las miniaturas de las fotos obtenidas de la API.
### Botones de Rover: 
Tres botones que representan los rovers disponibles: Curiosity, Opportunity, y Spirit.
## Uso de Filtros
### Seleccionar Sol: 
El usuario puede elegir un "sol" específico de los disponibles en el desplegable. Los "sols" representan días específicos en Marte. 
Los soles disponibles son:

1000 -
1020 -
1040 -
1060 -
1080 -
1100 -
1120 -
1140 -
2000 -
3000
### Seleccionar Cámara: 
El usuario puede elegir una cámara del rover de una lista predefinida. 
Las cámaras disponibles son:

FHAZ -
RHAZ -
MAST -
CHEMCAM -
MAHLI -
MARDI -
NAVCAM -
PANCAM -
MINITES
## Búsqueda de Fotos
### Seleccionar Rover: 
El usuario debe hacer clic en uno de los botones de rover (Curiosity, Opportunity, o Spirit). Esto inicia una búsqueda de fotos para el rover seleccionado, utilizando los filtros de sol y cámara elegidos.
### Resultados de la Búsqueda: 
En el área de información se muestra un mensaje indicando que se está buscando fotos. Una vez que la búsqueda se completa, se muestra el número de resultados encontrados.
### Visualización de Fotos:
Las fotos obtenidas se muestran como miniaturas en el panel de miniaturas. Puede hacer clic en una miniatura para ver la imagen completa.
## Visualización de Imágenes
### Abrir URL de la Imagen: 
Cada miniatura tiene un enlace a la URL de la imagen. Al hacer clic en la URL, se abre el navegador web predeterminado para mostrar la imagen.
## Ver Imagen Completa: 
Al hacer clic en la miniatura, se abre una nueva ventana mostrando la imagen en tamaño completo.
## Mensajes de Error
La aplicación maneja posibles errores durante la búsqueda y carga de fotos. Los mensajes de error se muestran en el área de información, informando sobre cualquier problema encontrado.
