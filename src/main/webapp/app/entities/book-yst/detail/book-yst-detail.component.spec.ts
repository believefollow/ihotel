import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookYstDetailComponent } from './book-yst-detail.component';

describe('Component Tests', () => {
  describe('BookYst Management Detail Component', () => {
    let comp: BookYstDetailComponent;
    let fixture: ComponentFixture<BookYstDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BookYstDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ bookYst: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(BookYstDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookYstDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bookYst on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookYst).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
