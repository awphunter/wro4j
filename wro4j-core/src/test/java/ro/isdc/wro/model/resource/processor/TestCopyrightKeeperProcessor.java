/**
 * Copyright wro4j@2011
 */
package ro.isdc.wro.model.resource.processor;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.processor.impl.CopyrightKeeperProcessor;
import ro.isdc.wro.model.resource.processor.impl.css.CssMinProcessor;
import ro.isdc.wro.util.WroTestUtils;

/**
 * @author Alex Objelean
 */
public class TestCopyrightKeeperProcessor {
  @Before
  public void setUp() {

  }
  @Test
  public void testCopyrightStripperProcessor()
      throws Exception {
    final ResourcePreProcessor decoratedProcessor = new CssMinProcessor();
    final ResourcePreProcessor processor = new CopyrightKeeperProcessor(decoratedProcessor);
    final URL url = getClass().getResource("copyright");

    final File testFolder = new File(url.getFile(), "test");
    final File expectedFolder = new File(url.getFile(), "expected");
    WroTestUtils.compareFromDifferentFoldersByExtension(testFolder, expectedFolder, "css", processor);
  }

  @Test
  public void testCopyrightAwareProcessor()
      throws Exception {
    //This procesor won't remove copyright headers.
    final ResourcePreProcessor decoratedProcessor = new ResourcePreProcessor() {
      public void process(final Resource resource, final Reader reader, final Writer writer)
        throws IOException {
        IOUtils.copy(reader, writer);
      }
    };
    final ResourcePreProcessor processor = new CopyrightKeeperProcessor(decoratedProcessor);
    final URL url = getClass().getResource("copyright");

    final File testFolder = new File(url.getFile(), "test");
    final File expectedFolder = new File(url.getFile(), "expectedCopyrightAware");
    WroTestUtils.compareFromDifferentFoldersByExtension(testFolder, expectedFolder, "css", processor);
  }
}
