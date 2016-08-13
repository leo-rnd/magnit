<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output omit-xml-declaration="yes" indent="yes"/>
 <xsl:strip-space elements="*"/>

 <xsl:template match="/*">
     <entries>
      <xsl:apply-templates/>
     </entries>
 </xsl:template>
 
 <xsl:template match="entry">
    <entry field="{/field}">
        <xsl:apply-templates/>
    </entry>
 </xsl:template>
</xsl:stylesheet>