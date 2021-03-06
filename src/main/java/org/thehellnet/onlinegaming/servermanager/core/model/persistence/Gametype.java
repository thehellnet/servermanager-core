package org.thehellnet.onlinegaming.servermanager.core.model.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "gametype")
public class Gametype implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gametype_id_seq")
    @SequenceGenerator(name = "gametype_id_seq", sequenceName = "gametype_id_seq")
    private Long id;

    @Basic
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "gametype", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<GameGametype> gameGametypes = new HashSet<>();

    public Gametype() {
    }

    public Gametype(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GameGametype> getGameGametypes() {
        return gameGametypes;
    }

    public void setGameGametypes(Set<GameGametype> gameGametypes) {
        this.gameGametypes = gameGametypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gametype gametype = (Gametype) o;
        return id.equals(gametype.id) &&
                name.equals(gametype.name) &&
                gameGametypes.equals(gametype.gameGametypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
