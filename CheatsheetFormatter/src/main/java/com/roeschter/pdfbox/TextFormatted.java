package com.roeschter.pdfbox;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class TextFormatted extends Text {

	PDFont font;
	float fontSize;
	public String text;

	float yOffset = 0;

	public TextFormatted( String _text, PDFont _font, float size) {
		font = _font;
		fontSize = size;
		text = _text;
	}

	@Override
	public TextFormatted clone() {
		return new TextFormatted( text, font, fontSize);
	}

	@Override
	public void layout() {
		if ( width != 0 )
			return;
		try {
			height = fontSize;
			width = getTextWidth( text, font, fontSize);
		} catch (Exception e ) {
			e.printStackTrace();
		}

	}

	public void trace(float x, float y, String text) {
		System.out.println("("+x+","+y+"):"+text);
	}

	@Override
	public void render( RenderContext ctx ) throws Exception {
		PDPageContentStream contentStream = ctx.contentStream;
		contentStream.beginText();
		contentStream.setFont( font, fontSize);
		float yPos = ctx.yPos - height - yOffset;
		trace(ctx.xPos, yPos, text );
		contentStream.newLineAtOffset( ctx.xPos , yPos);
		contentStream.showText(text);
		contentStream.endText();
		ctx.xPos += width;
	}
}
