<c:if test="${param.error != null}">
    <div id="error">
        <spring:message code="message.badCredentials">
        </spring:message>
    </div>
</c:if>