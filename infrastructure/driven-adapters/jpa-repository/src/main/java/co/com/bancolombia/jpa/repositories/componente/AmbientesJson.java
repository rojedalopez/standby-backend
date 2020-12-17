package co.com.bancolombia.jpa.repositories.componente;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class AmbientesJson implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{ Types.JAVA_OBJECT };
    }

    @Override
    public Class<AmbienteData[]> returnedClass() {
        return AmbienteData[].class;
    }


    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null) {
            return y == null;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        final String cellContent = resultSet.getString(names[0]);
        if (cellContent == null) {
            return new AmbienteData[0];
        }
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(cellContent.getBytes("UTF-8"), returnedClass());
        } catch (final Exception ex) {
            throw new RuntimeException("Failed to convert String to Invoice: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object value, int idx, SharedSessionContractImplementor session) throws HibernateException, SQLException {

        if (value == null) {
            ps.setNull(idx, Types.OTHER);
            return;
        }
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final StringWriter w = new StringWriter();
            mapper.writeValue(w, value);
            w.flush();
            ps.setObject(idx, w.toString(), Types.OTHER);
        } catch (final Exception ex) {
            throw new RuntimeException("Failed to convert Invoice to String: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        return value;
    }


    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }


    @Override
    public boolean isMutable() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (AmbienteData[]) this.deepCopy(value);
    }


    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
