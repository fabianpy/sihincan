package pacientes

class TipoDocumento {
    String codigo
    String nombre

    static constraints = {
        codigo size:3, blank: false, unique:true
        nombre size: 70, blank: false
    }
}
