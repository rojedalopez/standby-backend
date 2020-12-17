package co.com.bancolombia.jpa.repositories.usuario;

import co.com.bancolombia.jpa.repositories.ciclo.DetalleCicloData;
import co.com.bancolombia.jpa.repositories.clavevalor.ClaveValorData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "usuario")
public class UsuarioData {

    @Id
    private String id_usuario;

    @Column
    private String password;

    @Column
    private String hash;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String direccion;

    @Column
    private String celular;

    @Column
    private String contacto;

    @Column
    private String celularcon;

    @Column
    private Date fecha_nacimiento;

    @ManyToOne
    @JoinColumn(name = "fk_subdominio", referencedColumnName = "id_clavevalor")
    private ClaveValorData fk_subdominio;

    @Column
    private int nivel;

    @Column
    private boolean activo;

    @Column
    private boolean bienvenido = true;

    @Column
    private boolean actualizar = true;

    /*@OneToMany
    private List<UsuarioData> usuarioDataList;

    @OneToMany
    private List<DetalleCicloData> detalleCicloDataList;

    @OneToMany(mappedBy = "fk_usuario")
    private Set<DetalleCicloData> detalleCicloDataSet;*/
}
