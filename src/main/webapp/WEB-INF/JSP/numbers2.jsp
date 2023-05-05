<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Skaičiai</title>
    <jsp:include page="header.jsp"/>
        <style>
                th{text-align:center}
            </style>
</head>
<body class="container">
    <div class="table-responsive">
        <table class="table table-striped">
            <tr>
                <th>Pirmas skaičius</th>
                <th>Ženklas</th>
                <th>Antras skaičius</th>
                <th>Rezultatas</th>
                <th>Veiksmas</th>
            </tr>

            <!-- iteruoja per visą skaičių sarašą -->
            <c:forEach var="numberBetKoks" items="${numbers}">

                <!-- konstruoja įrašo atnaujinimo adresą su skaičiaus id -->
                <c:url var="atnaujinti" value="/update">
                    <c:param name="id" value="${numberBetKoks.id}"/>
                </c:url>

                <!-- konstruoja įrašo trynimo adresą su skaičiaus id -->
                <c:url var="trinti" value="/delete">
                    <c:param name="id" value="${numberBetKoks.id}"/>
                </c:url>

                <!-- konstruoja įrašo peržiūros adresą su skaičiaus id -->
                <c:url var="rodyti" value="/show">
                    <c:param name="id" value="${numberBetKoks.id}"/>
                </c:url>

                <tr>
                    <td>${numberBetKoks.sk1}</td>
                    <td>${numberBetKoks.action}</td>
                    <td>${numberBetKoks.sk2}</td>
                    <td>${numberBetKoks.result}</td>

                    <td>
                        <!-- atvaizduoti atnaujinimo adresą --> <a href="${atnaujinti}">Keisti</a>
                        | <a href="${trinti}"
                             onclick="if (!(confirm('Ar tikrai norite ištrinti šį įrašą?'))) return false">Trinti</a>
                        | <!-- atvaizduoti rodymo adresą --> <a href="${rodyti}">Rodyti</a>
                    </td>

                </tr>

            </c:forEach>

        </table>
    </div>
</body>
</html>
