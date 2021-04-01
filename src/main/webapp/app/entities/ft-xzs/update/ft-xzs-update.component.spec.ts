jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FtXzsService } from '../service/ft-xzs.service';
import { IFtXzs, FtXzs } from '../ft-xzs.model';

import { FtXzsUpdateComponent } from './ft-xzs-update.component';

describe('Component Tests', () => {
  describe('FtXzs Management Update Component', () => {
    let comp: FtXzsUpdateComponent;
    let fixture: ComponentFixture<FtXzsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ftXzsService: FtXzsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FtXzsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FtXzsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FtXzsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ftXzsService = TestBed.inject(FtXzsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const ftXzs: IFtXzs = { id: 456 };

        activatedRoute.data = of({ ftXzs });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ftXzs));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ftXzs = { id: 123 };
        spyOn(ftXzsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ftXzs });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ftXzs }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ftXzsService.update).toHaveBeenCalledWith(ftXzs);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ftXzs = new FtXzs();
        spyOn(ftXzsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ftXzs });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ftXzs }));
        saveSubject.complete();

        // THEN
        expect(ftXzsService.create).toHaveBeenCalledWith(ftXzs);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ftXzs = { id: 123 };
        spyOn(ftXzsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ftXzs });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ftXzsService.update).toHaveBeenCalledWith(ftXzs);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
