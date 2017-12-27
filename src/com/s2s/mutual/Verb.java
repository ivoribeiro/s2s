package com.s2s.mutual;

public class Verb {
    /**
     * Check if a verb exists
     *
     * @param verb
     * @return
     */
    public static VerbEnum exists(String verb) {
        VerbEnum exists = null;
        if (VerbEnum.POST.toString().equals(verb)) {
            exists = VerbEnum.POST;
        } else {
            if (VerbEnum.GET.toString().equals(verb)) {
                exists = VerbEnum.GET;
            } else {
                if (VerbEnum.DELETE.toString().equals(verb)) {
                    exists = VerbEnum.DELETE;
                } else {
                    if (VerbEnum.POST.toString().equals(verb)) {
                        exists = VerbEnum.PUT;
                    } else {
                        if (VerbEnum.SUCCESS.toString().equals(verb)) {
                            exists = VerbEnum.SUCCESS;
                        } else {
                            if (VerbEnum.ERROR.toString().equals(verb)) {
                                exists = VerbEnum.ERROR;
                            } else {
                                if (VerbEnum.INFO.toString().equals(verb)) {
                                    exists = VerbEnum.INFO;
                                }
                            }
                        }
                    }
                }
            }
        }
        return exists;
    }
}
