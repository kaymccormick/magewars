#!/usr/bin/perl

while(<>) {
    chomp;
    my $f = $_;
    if(!open(FILE, $_))
    {
	next;
    }
    open(OUTFILE, ">$_.new");
    while(<FILE>) {
#	print;
	chomp;
	if(/^\s*import\s+(javafx\.beans\..*)\s*;/) {
	    $class = $1;
	    print join("\t", $f, "import", $class), "\n";
	}
	if(/^\s*(?:(public|private|protected)\s+)?(Simple.*Property(?:<.*>)?)\s+(\S+)\s*=\s*(.*)$/) {
	    my $vis = $1;
	    my $class = $2;
	    my $name = $3;
	    print join("\t", $f, "member", $vis, $class, $name), "\n";
	    my $type;
	    if($class =~ /SimpleObjectProperty<(.*)>/) {
		$type = $1;
		print $type, "\n";
		
	    } elsif ($class =~ m!Simple(.*)Property!) {
		$type = $1;
		print "basic: $type\n";
	    }
	    print "new: $vis $type $name; /* $_ /\n";
	}
	print OUTFILE $_, "\n";
    }
    close(FILE);
    close(OUTFILE);
    next;
}
