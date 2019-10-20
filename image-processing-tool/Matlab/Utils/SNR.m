function [snr] = SNR(imgI, imgB)
    Pinitial = sum(imgI(:).^2);
    Pbruite = sum((imgB(:)-imgI(:)).^2);
    snr = 10*log10(Pinitial/Pbruite);
end

