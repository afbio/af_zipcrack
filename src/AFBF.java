public class AFBF {

    public static final void main(String[] args) {

        ParamsValidator paramsValidator = new ParamsValidator();

        if (paramsValidator.isValid(args) == false) {
            System.out.println(paramsValidator.getMessage());
            return;
        }

        Extractor extractor = new Extractor();

        extractor.setCompressedFilePath(args[0]);
        extractor.setPasswordsFilePath(args[1]);

        extractor.execute();

        System.out.println(extractor.getMessage());
    }


}
