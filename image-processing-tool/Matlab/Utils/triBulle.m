function [x] = triBulle(L)
    n = length(L);
    L = L(:).';
    %L'instruction .' permet de transformer la Liste L colonne ou ligne en
    %ligne
    for j=1:n-1
        for i=1:n-1
            if L(i)>L(i+1)
                L([i,i+1]) = L([i+1,i]);
            end;
        end;
    end;
    x = L;
end

