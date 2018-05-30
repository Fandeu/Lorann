package model.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Map;

/**
 * <h1>The Class MapDAO.</h1>
 *
 * @author Jean-Aymeric DIET jadiet@cesi.fr
 * @version 1.0
 */
public abstract class MapDAO extends AbstractDAO {

    /** The sql map by id. */
    private static String sqlMapById   = "{call findMapById(?)}";

    /** The sql map by name. */
    private static String sqlMapByName = "{call findMapByName(?)}";

    /** The sql all maps. */
    private static String sqlAllMaps   = "{call findAllMaps()}";

    /** The id column index. */
    private static int    idColumnIndex    = 1;

    /** The name column index. */
    private static int    nameColumnIndex  = 2;

    /**
     * Gets the map by id.
     *
     * @param id
     *            the id
     * @return the map by id
     * @throws SQLException
     *             the SQL exception
     */
    public static Map getMapById(final int id) throws SQLException {
        final CallableStatement callStatement = prepareCall(sqlMapById);
        Map map = null;
        callStatement.setInt(1, id);
        if (callStatement.execute()) {
            final ResultSet result = callStatement.getResultSet();
            if (result.first()) {
                map = new Map(result.getInt(idColumnIndex), result.getString(nameColumnIndex));
            }
            result.close();
        }
        return map;
    }

    /**
     * Gets the map by name.
     *
     * @param name
     *            the name
     * @return the map by name
     * @throws SQLException
     *             the SQL exception
     */
    public static Map getMapByName(final String name) throws SQLException {
        final CallableStatement callStatement = prepareCall(sqlMapByName);
        Map map = null;

        callStatement.setString(1, name);
        if (callStatement.execute()) {
            final ResultSet result = callStatement.getResultSet();
            if (result.first()) {
                map = new Map(result.getInt(idColumnIndex), result.getString(nameColumnIndex));
            }
            result.close();
        }
        return map;
    }

    /**
     * Gets the all maps.
     *
     * @return the all maps
     * @throws SQLException
     *             the SQL exception
     */
    public static List<Map> getAllMaps() throws SQLException {
        final ArrayList<Map> maps = new ArrayList<Map>();
        final CallableStatement callStatement = prepareCall(sqlAllMaps);
        if (callStatement.execute()) {
            final ResultSet result = callStatement.getResultSet();

            for (boolean isResultLeft = result.first(); isResultLeft; isResultLeft = result.next()) {
                maps.add(new Map(result.getInt(idColumnIndex), result.getString(nameColumnIndex)));
            }
            result.close();
        }
        return maps;
    }
}
