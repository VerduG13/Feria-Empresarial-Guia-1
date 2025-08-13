# Feria-Empresarial-Guia-1
Proyecto de Feria Empresarial en Java curso Desarrollo de Software EAN
Feria Empresarial (Java 17, consola)
Backend sencillo para gestionar una feria empresarial desde consola: empresas, stands, visitantes y comentarios con calificación. Persistencia en memoria (listas) para facilitar la práctica y la evaluación por pasos.

✨ Funcionalidades 
Empresas: registrar, listar, editar, eliminar (unicidad por nombre).
Stands: crear, listar (todos / disponibles / ocupados), asignar/desasignar a empresa (unicidad por número).
Visitantes: registrar, listar, editar, eliminar (unicidad por identificación).
Comentarios: visitante comenta un stand con fecha, texto y calificación (1–5); cálculo de promedio por stand.

Reportes:

Empresas ↔ Stands (con/sin stand, y stands sin asignar).

Visitantes ↔ Stands visitados (derivado de comentarios).

Promedio de calificación por stand (ordenado).

Persistencia: listas en memoria. Al cerrar el programa, los datos se pierden (comportamiento intencional para la guía 1).

🧱 Arquitectura
model/ → entidades y tipos: Empresa, Stand, StandSize, Visitante, Comentario (record).

service/ → lógica de negocio: FeriaEmpresarial.

utils/ → utilidades de consola: InputUtils.

Main → menú de texto para probar casos de uso.

🛠️ Requisitos
Java 17
Maven 3.8+
IntelliJ IDEA 

🚀 Cómo ejecutar
IntelliJ (recomendada) u otra IDE
Importar como Proyecto Maven.

Ejecuta org.example.Main (botón Run).

🧭 Menú (resumen)
Opción	Acción
1	Registrar empresa
2	Listar empresas
3	Crear stand
4	Listar stands (todos/disponibles/ocupados)
5	Asignar stand a empresa
6	Desasignar stand
7	Registrar visitante
8	Listar visitantes
9	Registrar comentario a un stand
10	Ver comentarios y promedio de un stand
11	Editar visitante
12	Eliminar visitante
13	Reporte: Empresas y Stands
14	Reporte: Visitantes y Stands visitados
15	Reporte: Promedio de calificación por Stand
0	Salir

Entrada por consola validada con InputUtils: enteros, rango (ej. 1–5), y textos no vacíos.
