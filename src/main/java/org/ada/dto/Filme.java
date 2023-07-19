package org.ada.dto;


public record Filme(Integer id, String title, String overview, String original_language, String vote_count, String vote_average)
        implements Comparable<Filme> {


    @Override
    public int compareTo(Filme o) {
        int comparacaoId = this.id.compareTo(o.id());
        if(comparacaoId != 0)
            return comparacaoId;

        return this.title.compareTo(o.title());
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", nome='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", original_language=" + original_language +
                ", vote_count=" + vote_count +
                ", vote_average=" + vote_average +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        System.out.println("Filme::equals");

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filme that = (Filme) o;

        if (!id.equals(that.id)) return false;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }
}



