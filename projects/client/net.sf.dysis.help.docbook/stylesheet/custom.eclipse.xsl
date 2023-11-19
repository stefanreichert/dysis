<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:import href="file:/D:/Software/DocBook/docbook-xsl-1.73.2/eclipse/eclipse.xsl" />
    <xsl:param name="base.dir">html/</xsl:param>
    <xsl:param name="html.stylesheet">style/book.css</xsl:param>
    <xsl:param name="create.plugin.xml" select="no"/>
    <xsl:param name="chunk.first.sections" select="1" />
    <xsl:param name="chunk.section.depth" select="3" />
    <xsl:param name="use.id.as.filename" select="0" />
    <xsl:param name="suppress.navigation" select="1" />
    <xsl:param name="chapter.autolabel" select="0" />
    <xsl:param name="generate.section.toc.level" select="0"/>
    <xsl:param name="table.borders.with.css" select="0"/>
    <xsl:param name="table.cell.border.thickness" select="1"/>
    <xsl:param name="html.cellspacing" select="0"/>
    <xsl:param name="html.cellpadding" select="10"/>
    <xsl:param name="html.cleanup" select="1"/>
    <xsl:param name="generate.toc">
        appendix toc,title
        article/appendix nop
        article toc,title
        book nop
        chapter nop
        part nop
        preface toc,title
        qandadiv toc
        qandaset toc
        reference toc,title
        sect1 toc
        sect2 toc
        sect3 toc
        sect4 toc
        sect5 toc
        section toc
        set toc,title
    </xsl:param>
</xsl:stylesheet>