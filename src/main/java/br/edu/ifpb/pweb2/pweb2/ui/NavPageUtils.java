package br.edu.ifpb.pweb2.pweb2.ui;

import org.springframework.data.domain.Page;

public class NavPageUtils {

    public static NavPage createNavePageFromPageWithSize(Page<?> page, int size) {
        return NavePageBuilder.newNavPage(page.getNumber() + 1, page.getTotalElements(),
                page.getTotalPages(), size);
    }
}
