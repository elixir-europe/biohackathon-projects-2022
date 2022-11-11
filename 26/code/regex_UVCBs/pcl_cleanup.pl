#!/usr/bin/perl

use strict;
use warnings;

my $file = "CID-Synonym-filtered-sort.gz";
my $cfile = "cid_uvcb_name_with_cid.tsv";  # Likely UVCB names associated with a CID
my $nfile = "cid_non_uvcb_name_with_cid.tsv";  # Possible non-UVCB names associated with a CID


# Compare names to those in PubChem CID space
my %s = ();
my %frag = ();
open( CID, "> $cfile" ) || die "Unable to write file: $cfile\n";
#open( NID, "> $nfile" ) || die "Unable to write file: $nfile\n";
my $nnam = 0;
my $cid = "";
my $s = "";
my $skip_read = 0;
open( NAM, "gunzip < $file |" ) || die "Unable to read file: $file\n";
while ( $_ = <NAM> ) {
  if ( $skip_read == 0 &&
       ( $_ = <NAM> ) ) {
    chop;
    ( $cid, $s ) = split( /	/, $_, 2 );
  } else {
    $skip_read = 0;
  }

  if ( exists( $s{ $s } ) ) {
    next;	  
  }

  $s{ $s } = undef;
  my $slen = length( $s );

  my $why = "";
  my $is_uvcb = 0;
  if ( $s =~ /C\d{1,3}\-C\d{1,3}/ ) {
    if ( ! ( 
             ( $slen == 36 &&
               $s =~ /\w{8}\-\w{4}\-\w{4}\-\w{4}\-\w{12}/ )
           ) ) {
      $is_uvcb = 1; $why = "C#-C# Regex";
#    } else {
#      print STDERR "Rejected \"C#-# Regex\" :: $s\n";
    }
  }

  if ( $s =~ /C\d{1,3}\-\d{1,3}/ ) {
    if ( ! ( 
             ( ! ( $s =~ /\s/ ) ) ||
             ( $slen == 9 &&
               $s =~ /C\d{3}\-\d{4}/ ) ||
             ( $slen == 36 &&
               $s =~ /\w{8}\-\w{4}\-\w{4}\-\w{4}\-\w{12}/ )
           ) ) {
      $is_uvcb = 1; $why = "C#-# Regex";
#    } else {
#      print STDERR "Rejected \"C#-# Regex\" :: $s\n";
    }
  }


  if ( $is_uvcb == 0 ) {
    if ( $s =~ /activated/i ) { $is_uvcb = 1; $why = "activated"; }
    elsif ( $s =~ /C\>\d{1,3}/ ) { $is_uvcb = 1; $why = "C># Regex"; }
    elsif ( $s =~ /C\<\d{1,3}/ ) { $is_uvcb = 1; $why = "C<# Regex"; }
    elsif ( $s =~ /acids/i &&
            ! ( $s =~ /acids\w/i ) ) {
      $is_uvcb = 1; $why = "acids";
    }
    elsif ( $s =~ /adduct/i ) { $is_uvcb = 1; $why = "adduct"; }
    elsif ( $s =~ /alcohols/i ) { $is_uvcb = 1; $why = "alcohols"; }
    elsif ( $s =~ /alc\./i ) { $is_uvcb = 1; $why = "alc."; }
    elsif ( $s =~ /alcs/i ) { $is_uvcb = 1; $why = "alcs"; }
    elsif ( $s =~ /alkane/i ) { $is_uvcb = 1; $why = "alkane"; }
    elsif ( $s =~ /alkanol/i ) { $is_uvcb = 1; $why = "alkanol"; }
    elsif ( $s =~ /alkoxy/i ) { $is_uvcb = 1; $why = "aloxy"; }
    elsif ( $s =~ /alkyl/i ) { $is_uvcb = 1; $why = "alkyl"; }
    elsif ( $s =~ /[\,\; ]alloy/i ) { $is_uvcb = 1; $why = "alloy"; }
    elsif ( $s =~ /[\,\; ]amber/i ) { $is_uvcb = 1; $why = "amber"; }
    elsif ( $s =~ /amides/i &&
            ! ( $s =~ /amides\w/i ) ) {
      $is_uvcb = 1; $why = "amides";
    }
    elsif ( $s =~ /amines/i &&
            ! ( $s =~ /amines\w/i ) ) {
      $is_uvcb = 1; $why = "amines";
    }
    elsif ( $s =~ /analog/i ) { $is_uvcb = 1; $why = "analog"; }
    elsif ( $s =~ /beet /i ) { $is_uvcb = 1; $why = "beet "; }
    elsif ( $s =~ /benzylated/i ) { $is_uvcb = 1; $why = "benzylated"; }
    elsif ( $s =~ /branched/i ) { $is_uvcb = 1; $why = "branched"; }
    elsif ( $s =~ /brominated/i ) { $is_uvcb = 1; $why = "brominated"; }
    elsif ( $s =~ /burnt/i ) { $is_uvcb = 1; $why = "burnt"; }
    elsif ( $s =~ /butter/i &&
            ! ( $s =~ /butter\w/i ) ) {
      $is_uvcb = 1; $why = "butter";
    }
    elsif ( $s =~ /charcol/i ) { $is_uvcb = 1; $why = "charcol"; }
    elsif ( $s =~ /chlorinated/i ) { $is_uvcb = 1; $why = "chlorinated"; }
    elsif ( $s =~ /complex/i ) { $is_uvcb = 1; $why = "complex"; }
    elsif ( $s =~ /compounds/i ) { $is_uvcb = 1; $why = "compounds"; }
    elsif ( $s =~ /compd\./i ) { $is_uvcb = 1; $why = "compd."; }
    elsif ( $s =~ /compds\./i ) { $is_uvcb = 1; $why = "compds."; }
    elsif ( $s =~ /concentrate/i ) { $is_uvcb = 1; $why = "concentrate"; }
    elsif ( $s =~ /condensed/i ) { $is_uvcb = 1; $why = "condensed"; }
    elsif ( $s =~ /condensation/i ) { $is_uvcb = 1; $why = "condensation"; }
    elsif ( $s =~ /distillate/i ) { $is_uvcb = 1; $why = "distillate"; }
    elsif ( $s =~ /dervs/i ) { $is_uvcb = 1; $why = "dervs"; }
    elsif ( $s =~ /derivs/i ) { $is_uvcb = 1; $why = "derivs"; }
    elsif ( $s =~ /derivatives/i ) { $is_uvcb = 1; $why = "derivatives"; }
    elsif ( $s =~ /diluent/i ) { $is_uvcb = 1; $why = "diluent"; }
    elsif ( $s =~ /earth/i ) { $is_uvcb = 1; $why = "earth"; }
    elsif ( $s =~ /esters/i &&
            ! ( $s =~ /esters\w/i ) ) {
      $is_uvcb = 1; $why = "esters";
    }
    elsif ( $s =~ /ethers/i &&
            ! ( $s =~ /ethers\w/i ) ) {
      $is_uvcb = 1; $why = "ethers";
    }
    elsif ( $s =~ /explosion/i ) { $is_uvcb = 1; $why = "explosion"; }
    elsif ( $s =~ /extract/i ) { $is_uvcb = 1; $why = "extract"; }
    elsif ( $s =~ /ext\./i ) { $is_uvcb = 1; $why = "ext."; }
    elsif ( $s =~ /[\- ]fat[t ]/i ) { $is_uvcb = 1; $why = "-fat or \' fat\'"; }
    elsif ( $s =~ /fatty/i ) { $is_uvcb = 1; $why = "fatty"; }
    elsif ( $s =~ /fish/i &&
            ! ( $s =~ /fish\w/i ) ) {
      $is_uvcb = 1; $why = "fish";
    }
    elsif ( $s =~ /flower/i &&
            ! ( $s =~ /flower\w/i ) ) {
      $is_uvcb = 1; $why = "flower";
    }
    elsif ( $s =~ /flowers/i &&
            ! ( $s =~ /flowers\w/i ) ) {
      $is_uvcb = 1; $why = "flowers";
    }
    elsif ( $s =~ /fluid/i &&
            ! ( $s =~ /fluid\w/i ) ) {
      $is_uvcb = 1; $why = "fluid";
    }
    elsif ( $s =~ /fragment/i ) { $is_uvcb = 1; $why = "fragment"; }
#    elsif ( $s =~ /fruit/i ) { $is_uvcb = 1; $why = "fruit"; }
    elsif ( $s =~ /gum/i &&
            ! ( $s =~ /gum\w/i ) ) {
      $is_uvcb = 1; $why = "gum";
    }
    elsif ( $s =~ /hormone/i ) { $is_uvcb = 1; $why = "hormone"; }
    elsif ( $s =~ /hydrocarbon/i ) { $is_uvcb = 1; $why = "hydrocarbon"; }
    elsif ( $s =~ /hydrogel/i ) { $is_uvcb = 1; $why = "hydrogel"; }
    elsif ( $s =~ /hydrogenated/i ) { $is_uvcb = 1; $why = "hydrogenated"; }
    elsif ( $s =~ /hydrolized/i ) { $is_uvcb = 1; $why = "hydrolized"; }
    elsif ( $s =~ /hydrolyzed/i ) { $is_uvcb = 1; $why = "hydrolyzed"; }
    elsif ( $s =~ /hydrolyzates/i ) { $is_uvcb = 1; $why = "hydrolyzates"; }
    elsif ( $s =~ /ketones/i &&
            ! ( $s =~ /ketones\w/i ) ) {
      $is_uvcb = 1; $why = "ketones";
    }
    elsif ( $s =~ /lated/i ) { $is_uvcb = 1; $why = "lated"; }
    elsif ( $s =~ /linear/i &&
            ! ( $s =~ /linear\w/i ) ) {
      $is_uvcb = 1; $why = "linear";
    }
    elsif ( $s =~ /liq\./i ) { $is_uvcb = 1; $why = "liq."; }
#    elsif ( $s =~ /mace/i ) { $is_uvcb = 1; $why = "mace"; }
    elsif ( $s =~ /mixed/i ) { $is_uvcb = 1; $why = "mixed"; }
    elsif ( $s =~ /mixture/i ) { $is_uvcb = 1; $why = "mixture"; }
    elsif ( $s =~ /modified/i ) { $is_uvcb = 1; $why = "modified"; }
    elsif ( $s =~ /molasses/i ) { $is_uvcb = 1; $why = "molasses"; }
    elsif ( $s =~ /oil/i &&
            ! ( $s =~ /\woil/i ) ) {
      $is_uvcb = 1; $why = "oil";
    }
    elsif ( $s =~ /oligomer/i ) { $is_uvcb = 1; $why = "oligomer"; }
    elsif ( $s =~ /oxides/i &&
            ! ( $s =~ /oxides\w/i ) ) {
      $is_uvcb = 1; $why = "oxides";
    }
    elsif ( $s =~ /oxidized/i ) { $is_uvcb = 1; $why = "oxidized"; }
    elsif ( $s =~ /pigment/i ) { $is_uvcb = 1; $why = "pigment"; }
    elsif ( $s =~ /pills/i ) { $is_uvcb = 1; $why = "pills"; }
    elsif ( $s =~ /petrol/i ) { $is_uvcb = 1; $why = "petrol"; }
    elsif ( $s =~ /poly/i ) { $is_uvcb = 1; $why = "poly"; }
    elsif ( $s =~ /product/i ) { $is_uvcb = 1; $why = "product"; }
    elsif ( $s =~ /rare/i ) { $is_uvcb = 1; $why = "rare"; }
    elsif ( $s =~ /reaction/i ) { $is_uvcb = 1; $why = "reaction"; }
    elsif ( $s =~ /resin/i ) { $is_uvcb = 1; $why = "resin"; }
    elsif ( $s =~ /residue/i ) { $is_uvcb = 1; $why = "residue"; }
    elsif ( $s =~ /residuum/i ) { $is_uvcb = 1; $why = "residuum"; }
    elsif ( $s =~ /\-rich/i ) { $is_uvcb = 1; $why = "-rich"; }
    elsif ( $s =~ /root/i ) { $is_uvcb = 1; $why = "root"; }
    elsif ( $s =~ /rosin/i ) { $is_uvcb = 1; $why = "rosin"; }
    elsif ( $s =~ /salts/i &&
            ! ( $s =~ /salts\w/i ) ) {
      $is_uvcb = 1; $why = "salts";
    }
    elsif ( $s =~ /serum/i ) { $is_uvcb = 1; $why = "serum"; }
    elsif ( $s =~ /shell/i ) { $is_uvcb = 1; $why = "shell"; }
    elsif ( $s =~ /saponified/i ) { $is_uvcb = 1; $why = "saponified"; }
    elsif ( $s =~ /solution/i ) { $is_uvcb = 1; $why = "solution"; }
    elsif ( $s =~ /silazanes/i ) { $is_uvcb = 1; $why = "silazanes"; }
    elsif ( $s =~ /siloxanes/i ) { $is_uvcb = 1; $why = "siloxanes"; }
    elsif ( $s =~ /silicones/i ) { $is_uvcb = 1; $why = "silicones"; }
    elsif ( $s =~ /solvent/i ) { $is_uvcb = 1; $why = "solvent"; }
    elsif ( $s =~ /spirit/i ) { $is_uvcb = 1; $why = "spirit"; }
    elsif ( $s =~ /starch/i ) { $is_uvcb = 1; $why = "starch"; }
    elsif ( $s =~ /steroid/i ) { $is_uvcb = 1; $why = "steroid"; }
    elsif ( $s =~ /syrup/i ) { $is_uvcb = 1; $why = "syrup"; }
    elsif ( $s =~ /tallow/i ) { $is_uvcb = 1; $why = "tallow"; }
    elsif ( $s =~ /tannin/i ) { $is_uvcb = 1; $why = "tannin"; }
    elsif ( $s =~ / tree/i ) { $is_uvcb = 1; $why = " tree"; }
    elsif ( $s =~ /turmeric/i ) { $is_uvcb = 1; $why = "turmeric"; }
    elsif ( $s =~ /unsat\./i ) { $is_uvcb = 1; $why = "unsat."; }
    elsif ( $s =~ /unsaturated/i ) { $is_uvcb = 1; $why = "unsaturated"; }
    elsif ( $s =~ /unspecified/i ) { $is_uvcb = 1; $why = "unspecified"; }
    elsif ( $s =~ /wax/i ) { $is_uvcb = 1; $why = "wax"; }
    elsif ( $s =~ /wool/i ) { $is_uvcb = 1; $why = "wool"; }
    elsif ( $s =~ /whole/i ) { $is_uvcb = 1; $why = "whole"; }
  }


  # Output the name and set of CIDs
  if ( $is_uvcb != 0 ) {
    print CID "\"$why\"\t$s\t$cid";

    my $prev_s = $s;
    while ( $_ = <NAM> ) {
      chop;
      ( $cid, $s ) = split( /	/, $_, 2 );
      if ( $s eq $prev_s ) {
        print " $cid";
      } else {
        $skip_read = 1;
        last;
      }
    }
    print CID "\n";

#    my @frag = split( /[^A-Za-z]+/, $s );
#    foreach my $f ( @frag ) {
#      if ( exists( $frag{ $f } ) ) {
#        $frag{ $f }++;
#      } else {
#        $frag{ $f } = 1;
#      }
#    }

#  } else {
#    my @frag = split( /[^A-Za-z]+/, $s );
#    foreach my $f ( @frag ) {
#      if ( exists( $frag{ $f } ) ) {
#        $frag{ $f }++;
#      } else {
#        $frag{ $f } = 1;
#      }
   }
}
close( NAM );
close( CID );
#close( NID );

open( FRG, "> cid_frag_list.tsv" ) || die "Unable to write file: cid_frag_list.tsv\n";
my %nf = ();
foreach my $f ( keys( %frag ) ) {
  $nf{ $frag{$f} }{ $f } = undef;
}
foreach my $n ( sort rbynum( keys( %nf ) ) ) {
  foreach my $f ( sort( keys( %{ $nf{$n} } ) ) ) {
    print FRG "$n\t$f\n";
  }
}
close( FRG );

sub bynum { $a<=>$b };
sub rbynum { $b<=>$a };

